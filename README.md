<div style="text-align: center;">

<h1>Wallet Crypto</h1>
<span>by </span><a href="https://in2.es">in2.es</a>
<p><p>

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=in2workspace_wallet-crypto&metric=security_rating)](https://sonarcloud.io/dashboard?id=in2workspace_wallet-crypto)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=in2workspace_wallet-crypto&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=in2workspace_wallet-crypto)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=in2workspace_wallet-crypto&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=in2workspace_wallet-crypto)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=in2workspace_wallet-crypto&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=in2workspace_wallet-crypto)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=in2workspace_wallet-crypto&metric=ncloc)](https://sonarcloud.io/dashboard?id=in2workspace_wallet-crypto)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=in2workspace_wallet-crypto&metric=alert_status)](https://sonarcloud.io/dashboard?id=in2workspace_wallet-crypto)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=in2workspace_wallet-crypto&metric=coverage)](https://sonarcloud.io/summary/new_code?id=in2workspace_wallet-crypto)

</div>

## Introduction

The Wallet Crypto Component is an essential service within the Wallet Solution, focused on creating, managing, and storing cryptographic material for the Wallet.

## Main Features

### Key Generation Service
- Implement a robust private key generator.
- Integrate security measures to protect the generation process.

### Key Management Service
- Establish functions for Key management, rotation, and revocation.
- Implement security policies for DID administration.

### DID Generation Service
- Implement a DID generator.
- Integrate security measures to protect the generation process.

### DID Management Service
- Establish functions for DID management, rotation, and revocation.
- Implement security policies for DID administration.

### Cryptographic Storage Service
- Develop a secure system for private key storage.
- Implement encryption mechanisms to ensure confidentiality.

### Document Signing Service
- Enable document signing using the private key associated with a user's DID.
- Integrate seamlessly with DID Management for streamlined user experience.

## Installation
### Prerequisites
- [Docker Desktop](https://www.docker.com/)
- [Git](https://git-scm.com/)

### Dependencies for Installation
To install and run the Wallet-Crypto, you will need the following dependencies:

- **Wallet-Data**: This component is necessary for persisting Decentralized Identifiers (DIDs). For its installation, follow the guide provided here: [Wallet-Data Configuration Component](https://github.com/in2workspace/wallet-data.git).

- **Vault (HashiCorp Vault or Azure Key Vault)**: A Vault solution is required for securely storing the private keys associated with DIDs. You have the option to use either HashiCorp Vault or Azure Key Vault based on your preference. Refer to the respective documentation for installation and configuration:
  - [HashiCorp Vault Documentation](https://www.vaultproject.io/docs)
  - [Azure Key Vault Documentation](https://docs.microsoft.com/en-us/azure/key-vault/)

Ensure that these dependencies are set up and configured properly before proceeding with the Wallet Crypto Component setup.

## Configurations
Now that you have the necessary dependencies, you can configure the wallet-user-registry using the following docker-compose. Ensure to adjust the environment variables to match your Vault and Wallet-Data configurations.
* Wallet-Crypto Configuration
```yaml
wallet-crypto:
  image: in2kizuna/wallet-crypto:v2.0.0
  environment:
    OPENAPI_SERVER_URL: "http://localhost:8081"
    WALLET_DATA_URL: "http://wallet-data:8080/api/dids"
    SPRING_CLOUD_VAULT_AUTHENTICATION: "token"
    SPRING_CLOUD_VAULT_TOKEN: "<your-vault-token>"
    SPRING_CLOUD_VAULT_HOST: "<your-vault-host>"
    SPRING_CLOUD_VAULT_SCHEME: "http"
    SPRING_CLOUD_VAULT_PORT: "<your-vault-port>"
    SPRING_CLOUD_VAULT_KV_ENABLED: "true"
    APP_SECRET-PROVIDER_NAME: "hashicorp"
  ports:
    - "8081:8080"
```
**Important Note**:
> The provided configuration is for connecting to HashiCorp Vault. If you wish to connect to Azure Key Vault instead, you will need to adjust the following environment variables:
> - `SPRING_CLOUD_AZURE_KEYVAULT_SECRET_ENDPOINT`: Set this to your Azure Key Vault azureKeyVaultSecretProperties endpoint.
> - `SPRING_CLOUD_AZURE_KEYVAULT_APPCONFIGURATION`: Set this to your Azure Key Vault appConfiguration

## Project Status
The project is currently at version **2.0.0** and is in a stable state.

## Contact
For any inquiries or collaboration, you can contact us at:
* **Email:** [info@in2.es](mailto:info@in2.es)
* **Name:** IN2, Ingeniería de la Información
* **Website:** [https://in2.es](https://in2.es)

## Creation Date and Update Dates
* **Creation Date:** October 25, 2023
* **Last Updated:** December 15, 2023