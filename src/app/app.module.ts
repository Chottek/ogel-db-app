import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {MachineService} from "./services/machine.service";
import {HttpClientModule} from "@angular/common/http";
import { ReportComponent } from './components/report/report.component';

@NgModule({
  declarations: [
    AppComponent,
    ReportComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [MachineService],
  bootstrap: [AppComponent]
})
export class AppModule { }
