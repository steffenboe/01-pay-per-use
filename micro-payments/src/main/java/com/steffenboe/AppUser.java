package com.steffenboe;

import java.util.UUID;

import org.web3j.crypto.Credentials;

record AppUser(UUID appId, String userId, Credentials credentials) {

}
