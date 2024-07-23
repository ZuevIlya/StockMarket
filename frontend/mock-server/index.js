import express from "express"
import { apiRouter } from "./router.js";


const app = express();

const PORT = 3030;

app.use(express.json())
app.use('/api', apiRouter);

app.get('/', (req, res) => {
    res.status(200).send('hello there');
});

app.get('/ping', (req, res) => {
    res.status(200).send('ok');
})


app.listen(PORT, () => {
    console.log(`\nServer is running on http://localhost:${PORT}\n`);
})

