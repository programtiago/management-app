import { Component, OnInit, ViewChild, viewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatSelect, MatSelectChange } from '@angular/material/select';

@Component({
  selector: 'app-equipment-form',
  templateUrl: './equipment-form.component.html',
  styleUrl: './equipment-form.component.scss'
})
export class EquipmentFormComponent implements OnInit{

  selectedValueCreationWithAssignment: any;
  selectedValueDepartment: any;

  constructor(){}

  ngOnInit(): void {
    
  }

  valueChoosedChanged(event: MatSelectChange){
    this.selectedValueCreationWithAssignment = {
      value: event.value,
      text: event.source.triggerValue
    };
    this.selectedValueCreationWithAssignment = this.selectedValueCreationWithAssignment.text;
  }

  valueDepartmentSelectedChanged(event: MatSelectChange){
    this.selectedValueDepartment = {
      value: event.value,
      text: event.source.triggerValue
    }
    this.selectedValueDepartment = this.selectedValueDepartment.text
  }


}
