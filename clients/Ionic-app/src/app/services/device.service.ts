import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class DeviceService {
  private url: string = "https://casa-dashboard.herokuapp.com/light";
  private params = {
    status : 'on'
  };

  constructor(private http: HttpClient) { }

  changeLampStatus(): Observable<any> {
    const headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
    headers.set('charset', 'UTF-8');

    // const params = new HttpParams();
    // params.set('status', '0');
    

   var body = JSON.stringify(this.params);
   console.log(body)

    return this.http.post(this.url, {headers,body});
  }

}
