package br.com.fiap.cliente.gateway.clienteimpl;

import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.exception.ClienteException;
import br.com.fiap.cliente.gateway.cliente.CognitoGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.security.SecureRandom;

import static br.com.fiap.cliente.controller.cliente.util.PasswordGenerator.generateTemporaryPassword;

@Service
@Profile("!dev")
public class CognitoGatewayImpl implements CognitoGateway {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
    private static final int LENGTH = 12;

    private final CognitoIdentityProviderClient cognitoClient;

    private final String userPoolId;

    public CognitoGatewayImpl(
            @Value("${COGNITO_USER_POOL_ID}") String userPoolId,
            @Value("${COGNITO_REGION:us-east-1}") String cognitoRegion
    ) {
        this.userPoolId = userPoolId;
        this.cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.of(cognitoRegion))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Override
    public ClienteResponseDTO cadastrarUsuario(String email, String nome, ClienteResponseDTO clienteResponseDTO) {
        String tempPass;
        try {
            tempPass = generateTemporaryPassword();
            AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(email)
                    .userAttributes(
                            AttributeType.builder().name("email").value(email).build(),
                            AttributeType.builder().name("name").value(nome).build(),
                            AttributeType.builder().name("email_verified").value("true").build()
                    )
                    .temporaryPassword(tempPass)
                    .desiredDeliveryMediums(DeliveryMediumType.EMAIL)
                    .forceAliasCreation(false)
                    .build();
            cognitoClient.adminCreateUser(request);

            cognitoClient.adminAddUserToGroup(AdminAddUserToGroupRequest.builder()
                    .userPoolId(userPoolId)
                    .username(email)
                    .groupName("USER")
                    .build());


            System.out.println(" Usuário " + email + " criado com sucesso no Cognito.");

        } catch (UsernameExistsException e) {
            throw new ClienteException("Usuário já existe no Cognito: " + email);
        } catch (CognitoIdentityProviderException e) {
            throw new ClienteException("Erro Cognito: " + e.awsErrorDetails().errorMessage());
        }
        clienteResponseDTO.setSenha(tempPass);
        return clienteResponseDTO;
    }

    @Override
    public void atualizarUsuario(String email, String novoNome, String novoEmail) {
        try {
            AdminUpdateUserAttributesRequest updateRequest = AdminUpdateUserAttributesRequest.builder()
                    .userPoolId(userPoolId)
                    .username(email)
                    .userAttributes(
                            AttributeType.builder().name("name").value(novoNome).build(),
                            AttributeType.builder().name("email").value(novoEmail).build(),
                            AttributeType.builder().name("email_verified").value("true").build()
                    )
                    .build();

            cognitoClient.adminUpdateUserAttributes(updateRequest);

            System.out.println("Usuário " + email + " atualizado com sucesso no Cognito.");

        } catch (UserNotFoundException e) {
            throw new ClienteException("Usuário não encontrado no Cognito: " + email);
        } catch (InvalidParameterException e) {
            throw new ClienteException("Parâmetros inválidos para atualização no Cognito: " + e.getMessage());
        } catch (CognitoIdentityProviderException e) {
            throw new ClienteException("Erro Cognito: " + e.awsErrorDetails().errorMessage());
        }
    }



}

