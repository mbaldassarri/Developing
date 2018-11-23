var Gpio = require('onoff').Gpio;
var lamp = new Gpio(14, 'high');

var mqtt = require('mqtt'), url = require('url');
var mqtt_url = url.parse("mqtt://zbqlnwjk:bF4CAJfM5AGI@m15.cloudmqtt.com:12505");
const client = mqtt.connect(mqtt_url)
const topic_status_current = 'smarthome/status';
const topic_status_change = 'smarthome/light';

client.on('connect', function () {
  client.subscribe(topic_status_current)
  client.subscribe(topic_status_change, function (err) {
    if (!err) {
      client.publish(topic_status_current, lamp.readSync().toString())
    }
  })
  console.log('Current status: ', lamp.readSync() == 0 ? 'OFF' : 'ON');
})

client.on('message', function (topic, message) {
  if(topic == topic_status_change){
    if(message != lamp.readSync()){
      var newStatus = (lamp.readSync() ^ 1);
      lamp.writeSync(newStatus)
      client.publish(topic_status_current, newStatus.toString())
    } 
  }
})

process.on('SIGINT', function () {
  lamp.unexport();
  client.end()
});