require('dotenv-safe').config();
console.log(`[Discord.JS]=> Starting...`);

const { db } = require('./src/Utils/Bases');
const { Files } = require('./src/Utils/Functions');
const { Client, GatewayIntentBits, REST, Routes } = require("discord.js");

db.client = new Client({
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
for (let e in Events) db.client.on(e, Events[e].bind(null, db.client));

db.client.commands = [];
let Commands = Files('./src/Commands/', '../../Commands', 0, 1);

for (let c in Commands) db.client.commands.push({ name: c, ...Commands[c] });

let rest = new REST({ version: '10' }).setToken(process.env.BOT_TOKEN);
rest.put(Routes.applicationCommands(process.env.BOT_ID), { body: db.client.commands });

db.client.login(process.env.BOT_TOKEN)
  .catch((err) => console.log(`[Discord.JS BOT]=> Login Error:\n${err}`));

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