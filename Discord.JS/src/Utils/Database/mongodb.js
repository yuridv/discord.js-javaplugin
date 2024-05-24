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
  _id: { type: String },
  discord: { type: String, default: '', index: true },
  code: { type: Number, default: 0, index: true },
  money: { type: Number, default: 0 },
  money_total: { type: Number, default: 0 },
  level: { type: Number, default: 0 },
  xp: { type: Number, default: 0 },
  chests: { type: Number, default: 0 }
})

module.exports = {
  Player: mongoose.model('Player', Player)
}