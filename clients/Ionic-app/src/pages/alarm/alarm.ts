import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {AlarmService} from "../../app/services/alarm.service";

@Component({
  selector: 'page-alarm',
  templateUrl: 'alarm.html'
})
export class Alarm {
  state = 'ON/OFF'
  constructor(public navCtrl: NavController, private alarmService: AlarmService ) {
    this.alarmService.login().subscribe(data => {
      debugger;
      console.log("data " + data); this.state = data
    });

  }


}
