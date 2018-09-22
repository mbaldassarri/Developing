import { Component } from '@angular/core';

import { Alarm } from '../alarm/alarm';
import { Weather } from '../weather/weather';
import { Devices } from '../devices/devices';


@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tab1Root = Devices;
  tab2Root = Weather;
  tab3Root = Alarm;

  constructor() {

  }
}
