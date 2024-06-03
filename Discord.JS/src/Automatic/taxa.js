const { db } = require('../Utils/Bases');
const { Errors, Timeout } = require('../Utils/Functions');
const { Server } = require('../Utils/Database/mongodb')
const emoji = require('../../emojis.json')

const { post } = require('axios');
const { EmbedBuilder } = require('discord.js');

const Auto = async (client) => {
  try {
    let taxa = Math.floor(Math.random() * (35 - 10 + 1)) + 10

    let server = await Server.findOne({ _id: "TODOS" });
    if (!server) server = new Server({ _id: "TODOS" });

    server.taxa = taxa;
    await server.save();

    try {
      await post(process.env.MINECRAFT_API + '/events/taxa', {
        headers: { 'Authorization': process.env.MINECRAFT_API_TOKEN }
      })
    } catch(e) {}

    await Timeout((Math.floor(Math.random() * (60 - 30 + 1)) + 30) * 60 * 1000);
    return Auto(client);
  } catch(err) {
    return Errors(err, `Automatic ${__filename}`)
      .then(() => Auto(client, r))
      .catch((e) => e)
  }
}

module.exports = Auto;