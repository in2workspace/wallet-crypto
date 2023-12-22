# Generate eIDAS digital certificate

## Prerequisits
- OpenSSL

## Getting Started

1. Edit, if you need, the eidas.conf file.
3. Open a terminal an go to the root of the eidas.conf file.
4. Execute the commant promnt below:
```
openssl req -new -config eidas.conf -keyout eidas.key -out eidas.csr
```
4. Add a password (e.g. in2pass)

## Resources
https://moose-tan-26kr.squarespace.com/blog/2020/01/13/how-to-generate-eidas-certificate