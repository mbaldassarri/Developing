var Gpio = require('onoff').Gpio;
var lamp = new Gpio(14, 'high');
const vars = require('./vars');
const dash_button = require('node-dash-button');
var dash = dash_button([vars.AMAZON_DASH_ALERT_MAC, vars.AMAZON_DASH_IOT_MAC], null, null, 'all'); 


dash.on("detected", function (dash_id){
  if (dash_id === vars.AMAZON_DASH_IOT_MAC){
    lamp.read(status =>
      (status === '0') ? lamp.writeSync(1) : lamp.writeSync(0));
      //type http post call to localhost Home-Assistant here to turn on lights
  }
});

bot.on('/hello', (msg) => {
  return bot.sendMessage(msg.from.id, `Ciao ${ msg.from.first_name }! Ti trovo bene oggi ! \u{1F604}`);
});

bot.on('/accendi_luci', (msg) => {
  //http post call to localhost HA to turn on lights
  bot.sendMessage(msg.from.id, `${ msg.from.first_name }, ho acceso le luci !`);
});

bot.on('/spegni_luci', (msg) => {
  bot.sendMessage(msg.from.id, `${ msg.from.first_name }, ho spento le luci !`);
});