import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { Alarm } from '../pages/alarm/alarm';
import { Devices } from '../pages/devices/devices';
import { Weather } from '../pages/weather/weather';
import { TabsPage } from '../pages/tabs/tabs';
import { HttpModule } from '@angular/http';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import {AlarmService} from "./services/alarm.service";
import {HttpClientModule} from "@angular/common/http";
import { DeviceService } from './services/device.service';

@NgModule({
  declarations: [
    MyApp,
    Alarm,
    Weather,
    Devices,
    TabsPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp),
    HttpModule,
    HttpClientModule
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    Alarm,
    Devices,
    Weather,
    TabsPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    AlarmService,
    DeviceService
  ]
})
export class AppModule {}
