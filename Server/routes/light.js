var express = require('express');
var router = express.Router();
var mqtt = require('mqtt'), url = require('url');
var mqtt_url = url.parse(process.env.CLOUDMQTT_URL || 'mqtt://localhost:1883');
var currentStatus = '';
const topic_status_current = 'smarthome/status';
const topic_status_change = 'smarthome/light';
const client = mqtt.connect(mqtt_url)

client.on('connect', function () {
  client.subscribe(topic_status_current)
  client.subscribe(topic_status_change)
})

client.on('message', function (topic, message) {
  if(topic == topic_status_current) {
    currentStatus = message.toString();
  }
})

router.post('/', function(req, res) {
  if(req.body.status != currentStatus) {
    client.publish(topic_status_change, req.body.status)
  } 
  res.send({status: currentStatus})
});

router.get('/', function(req, res){
  res.send({status: currentStatus});
})

module.exports = router;

