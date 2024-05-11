const mongoose = require('mongoose');

mongoose.set('strictQuery', true);
mongoose.connect(process.env.MONGO_DB)
  .then(() => { 
    console.log(`[Database MongoDB]=> Connected Successfully!`);
  })
  .catch((err) => { 
    console.log(`[Database MongoDB]=> Connection Error: ${err}`);
  });

let Player = new mongoose.Schema({
  _id: String,
  nickname: String,
  money: Number,
  level: Number,
  xp: Number
})

module.exports = {
  Player: mongoose.model('Player', Player)
}