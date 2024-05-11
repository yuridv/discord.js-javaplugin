require('dotenv-safe').config();
console.log(`[Discord.JS]=> Starting...`);

const Express = require('express');
const express = Express();

const server = require('http').createServer(express);

const routes = require('./src/Routes/routes');

express
  .use(Express.json())

  .get('*', routes)
  .post('*', routes)
  .delete('*', routes)
  .put('*', routes)

server
  .listen(process.env.PORT || 3000, async (err) => {
    if (err) return console.log(`[Listen Error]=> `, err)
    console.log(`[API RESTful]=> Started Successfully!`)
  });

const mongodb = require('./src/Utils/Database/mongodb')

const { Files } = require('./src/Utils/Functions');
const { Client, GatewayIntentBits, REST, Routes } = require("discord.js");

const client = new Client({
  status: 'online',
  autoReconnect: true,
  interval: 60,
  retryLimit: 35,
  afk: false,
  compress: true,
  intents: [ 
    GatewayIntentBits.MessageContent, // INTENT PRIVADA
    GatewayIntentBits.GuildPresences, // INTENT PRIVADA
    GatewayIntentBits.Guilds, // INTENT PRIVADA
    GatewayIntentBits.GuildMessages,
    GatewayIntentBits.GuildMembers,
    GatewayIntentBits.GuildModeration,
  ],
});

let Events = Files('./src/Events/', '../../Events', 0, 1);
for (let e in Events) client.on(e, Events[e].bind(null, client));

client.commands = [];
let Commands = Files('./src/Commands/', '../../Commands', 0, 1);

for (let c in Commands) client.commands.push({ name: c, ...Commands[c] });

let rest = new REST({ version: '10' }).setToken(process.env.TOKEN);
rest.put(Routes.applicationCommands(process.env.BOT_ID), { body: client.commands });

client.login(process.env.TOKEN)
  .catch((err) => console.log(`[Discord.JS BOT]=> Login Error:\n${err}`));