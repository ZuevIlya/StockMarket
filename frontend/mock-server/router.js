import { Router } from "express";
import { user } from "./mocks/user.js";

export const apiRouter = Router();

apiRouter.post('/auth/login', (req, res) => {
    const { username, password } = req.body;

    if (user.username === username && user.password === password){
        res.status(200).json({ message: 'ok' })
    } else {
        res.status(401).json({ message: 'user does not exist' })
    }
});

apiRouter.post('/auth/registration', (req, res) => {
    const { username } = req.body;

    if (user.username === username){
        res.status(401).json({ message: 'user already exists' })
    } else {
        res.status(200).json({ message: 'ok' })
    }
})