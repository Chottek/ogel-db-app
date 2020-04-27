import { Component, OnInit } from '@angular/core';
import {MachineService} from "../../services/machine.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  constructor(private machineService: MachineService, private route: ActivatedRoute) { }

  public machineData;
  public runtimeData;
  private param;
  public current_date = new Date();

  ngOnInit(): void {
    this.param = this.route.snapshot.queryParams["date"];    // get date parameter value
    this.current_date = new Date();     // get date for generate mark

    this.getMachineData(this.param);   // get data from
    this.getRuntimeData(this.param);   // machineService by param
  }

  getMachineData(date: string){
    this.machineService.getTotalValueOf(date).subscribe(
       data => { this.machineData = data},
        err => { console.error(err)},
      () => { console.log("done!")}
    );
  }

  getRuntimeData(date: string){
    this.machineService.getRuntime(date).subscribe(
      data => { this.runtimeData = data},
      err => { console.error(err)},
      () => { console.log("done!")}
    );
  }

  parseWarning(n : number){  //get number and return warning in string
    switch(n){
      case 0: return "GOOD";
      case 1: return "WARNING";
      case 2: return "FATAL!";
    }
  }

  parseColor(n : number){ //get number and return color for warning
    switch(n){
      case 0: return "#008000"; //Hex code for green
      case 1: return "#FFA500"; //Hex code for orange
      case 2: return "#FF0000"; //Hex code for red
    }
  }

  //TODO: Add <select> to choose date

}
