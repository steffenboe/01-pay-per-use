# taken from https://github.com/matthiaszimmermann/ganache-docker

# accounts
# 0x627306090abaB3A6e1400e9345bC60c78a8BEf57 (10000 ETH)

# private keys
# 0xc87509a1c067bbde78beb793e6fa76530b6382a4c0241e5e4a9ec0a0f44dc0d3

FROM node:alpine

WORKDIR /app 

# install ganache (local chain for testing)
RUN npm install -g ganache-cli

# install web3 and ethers api to interact with ganache
# this allows interactive usage of the container without any additional installs
#RUN npm install -g web3
#RUN npm install -g --save ethers

ENV NODE_PATH="/usr/local/lib/node_modules"
ENV NODE_OPTIONS=--openssl-legacy-provider

# ganache setup
# port: 8545 (ganache default port)
# mnemonic: "candy maple cake sugar pudding cream honey rich smooth crumble sweet treat"
# private key: 0xc87509a1c067bbde78beb793e6fa76530b6382a4c0241e5e4a9ec0a0f44dc0d3
# db path: /var/lib/ganache/data
CMD ["ganache-cli", "--port", "8545", "--mnemonic", "candy maple cake sugar pudding cream honey rich smooth crumble sweet treat", "--accounts", "10",  "--defaultBalanceEther", "10000", "--networkId", "1234", "--chainId", "1234", "--db", "/var/lib/ganache/data", "-h", "0.0.0.0"]