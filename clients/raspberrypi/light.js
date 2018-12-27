var Gpio = require('onoff').Gpio;
var lamp = new Gpio(14, 'high');

var mqtt = require('mqtt'), url = require('url');
var mqtt_url = url.parse("mqtt://zbqlnwjk:bF4CAJfM5AGI@m15.cloudmqtt.com:12505");
const client = mqtt.connect(mqtt_url)
const topic_status_current = 'smarthome/status';
const topic_status_change = 'smarthome/light';
var RaspiCam = require("raspicam");
var SftpUpload = require('sftp-upload')
const local_picture_path = "./photo/picture.jpg";

var camera = new RaspiCam({
	mode: "photo",
	output: local_picture_path,
	encoding: "jpg",
	timeout: 0 // take the picture immediately
});

//setInterval(function() {
  camera.start();
//}, 5000)

var options = {
		host:'192.168.9.100',
		username:'marco',
		password:'<marco>',
		port: 2435,
		path: './photo/',
		remoteDir: '/home/marco/smart_home/picam/'
}

//listen for the "read" event triggered when each new photo/video is saved
camera.on("read", function(err, timestamp, filename){
	var sftp = new SftpUpload(options);
	sftp.on('error', function(err) {
      throw err;
	})
	.on('uploading', function(progress) {
      console.log('Uploading', progress.file);
      console.log(progress.percent+'% completed');
	})
	.on('completed', function() {
      console.log('Upload Completed');
	}).upload();
});


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
