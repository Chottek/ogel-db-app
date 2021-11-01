import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ogel-ui';
}

const params = new URL(location.href).searchParams;
const date = params.get('date');

let dateReg = new RegExp('([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))'); // regex of yyyy-MM-dd date pattern

if(window.location.pathname !== '/report' ||  !dateReg.test(date)){   //Redirect from blank '/' page or wrong date parameter
  window.location.href = '/report?date=2018-01-01';
}

