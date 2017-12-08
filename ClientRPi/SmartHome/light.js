const mqtt = require('mqtt');
var Gpio = require('onoff').Gpio;
var lamp = new Gpio(18, 'high');
const vars = require('./vars');
const dash_button = require('node-dash-button');
var dash = dash_button([vars.AMAZON_DASH_ALERT_MAC, vars.AMAZON_DASH_IOT_MAC], null, null, 'all'); 

const client = mqtt.connect('http://insert-mqtt-broker-address-here:8883', {username: 'username', password: 'password'})

client.on('connect', function () {
  client.subscribe('smarthome/light')
  console.log("OK CONNECTED")
})

client.on('message', function (topic, message) {
  if(topic == 'smarthome/light'){
	console.log(message.toString());
    if(message == '1'){
      lamp.writeSync(0);
    }
    if(message == '0'){
      lamp.writeSync(1);
    }
  }
  console.log(message.toString())
})


process.on('SIGINT', function () {
  lamp.unexport();
  client.end()
});

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