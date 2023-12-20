# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [v2.0.0] - 2023-12-15

### Added
- Add support for GitHub Actions for CI/CD.
- Checkstyle for code quality.
- Endpoint to sign documents with using your dids associated to your private key.
- Add SonarCloud for code quality.

## [v1.0.0] - 2023-12-4

### Added
- Implemented a robust private key generator with security measures to protect the generation process.
- Functions for key management, rotation, and revocation, and security policies for DID administration.
- Implemented a DID generator with security measures to protect the generation process.
- Functions for DID management, rotation, and revocation.
- Functions for DID management, rotation, and revocation. 
- Support for HashiCorp Vault and Azure Key Vault for securely storing private keys associated with DIDs. Added functionalities for saving, retrieving, and deleting secrets in a Vault system.
- Integration with Wallet-Data for persisting Decentralized Identifiers associated to a user.
- Creational Patterns, utilization of the Builder pattern
- Structural Patterns, implementation of the Facade pattern.
- Environment variable configuration for integrating with existing Vault instances and Wallet-Data component.
- Docker-compose configuration for easy deployment and setup
- Project status, contact information, and creation/update dates in README.

[release]:
[1.0.0]: https://github.com/in2workspace/wallet-crypto/releases/tag/1.0.0
[2.0.0]: https://github.com/in2workspace/wallet-crypto/releases/tag/v2.0.0