import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class AlarmService {
  private url: string = "https://app.e-daitem.com/mediator/authenticate/login";
  private credentials = {
    username : 'marco.baldassarri89@gmail.com',
    password : 'ZBYV97'
  };

  constructor(private http: HttpClient) { }

  login(): Observable<any> {
    const params = new HttpParams();
    params.set('username', 'marco.baldassarri89@gmail.com');
    params.set('password', 'ZBYV97');

    const headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
   // headers.set('charset', 'UTF-8');
   var body = JSON.stringify(this.credentials);
    return this.http.post(this.url, {headers,body});
  }

}
