import sha256 from 'js-sha256'
const pass = 'password';
const hash = sha256.sha256(pass);

export const user = {
    username: 'K', 
    password: hash,
}