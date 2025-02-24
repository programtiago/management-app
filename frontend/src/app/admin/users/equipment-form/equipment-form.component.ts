import { Component, OnInit } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { User } from '../../../model/user/user';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { CreateEquipmentAssignUserRequest } from '../../../model/equipment/equipment-create-assign-user';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StatusEquipment } from '../../../model/equipment/status-equipment';

@Component({
  selector: 'app-equipment-form',
  templateUrl: './equipment-form.component.html',
  styleUrl: './equipment-form.component.scss'
})
export class EquipmentFormComponent implements OnInit{

  selectedValueCreationAssignmentType: any
  selectedValueDepartment: any
  selectedUserId: any
  selectedCategory: any
  selectedWorkstation: any
  typeAssignmentSelected: string = '';

  users: User[] = [];

  createEquipmentForm!: FormGroup;
  createEquipmentAndAssignToUserForm!: FormGroup;

  categorys: string[] = ['Scanner', 'Desktop', 'Printer', 'Dockstation', 'Screen', 'Laptop']
  workstations: string[] = ['Unity A', 'Unity B', 'Unity C']

  constructor(private adminSerice: AdminService, private fb: FormBuilder, private router: Router, private location: Location, private snackBar: MatSnackBar){
    //Form to send the data equipment with assignment 
    this.createEquipmentAndAssignToUserForm = this.fb.group({
      description: [''],
      serialNumber: [''],
      brand: [''],
      model: [''],
      user: [''],
      category: [''],
      unity: [''],
      type: ['TYPE'],
      registryDate: [new Date().toString()],
      statusEquipment: [StatusEquipment.IN_USE],
      isActive: [true]
    })
    
    //Form to send the data equipment only with no assign
    this.createEquipmentForm = this.fb.group({
      description: [''],
      serialNumber: [''],
      brand: [''],
      model: [''],
      category: [''],
      unity: [''],
      type: ['TYPE'],
      registryDate: [new Date().toUTCString()],
      statusEquipment: [StatusEquipment.AVAILABLE],
      isActive: [true]
    })
  }

  ngOnInit(): void {
    this.adminSerice.listUsersActive().subscribe((res) => {
      this.users = res;
    })
  }

  valueChoosedChanged(event: MatSelectChange){
    this.selectedValueCreationAssignmentType = {
      value: event.value,
      text: event.source.triggerValue
    };
    this.selectedValueCreationAssignmentType = this.selectedValueCreationAssignmentType.text;
  }

  valueDepartmentSelectedChanged(event: MatSelectChange){
    this.selectedValueDepartment = {
      value: event.value,
      text: event.source.triggerValue
    }
    this.selectedValueDepartment = this.selectedValueDepartment.text
  }

  createEquipment(){
    const newEquipment: CreateEquipmentAssignUserRequest = this.createEquipmentForm.value;
    if (this.selectedValueCreationAssignmentType == 'None'){
      if (this.createEquipmentForm.valid){
        this.adminSerice.createEquipmentWithNoAssign(newEquipment).subscribe((res) => {
          if (res != null){
            this.onSucess();
            this.router.navigateByUrl("admin/equipments")
          }
        }), (error: any) => {
          this.onError();
        }
      }
    }
  }
  
  createEquipmentAndAssignToUser(userId: number){
    const newEquipment: CreateEquipmentAssignUserRequest = this.createEquipmentAndAssignToUserForm.value;
    if (this.selectedValueCreationAssignmentType == 'User'){
      if (this.createEquipmentAndAssignToUserForm.valid){
        this.adminSerice.createEquipmentAndAssignToUser(newEquipment, userId).subscribe((res) => {
          if (res != null){
            this.router.navigateByUrl("admin/equipments")
          }
        }), (error: any) => {
          console.log(error)
        }
      }
    }
  }

  onCancel(){
    this.location.back();
  }

  private onSucess(){
    this.snackBar.open('Equipment created sucessfully', '', { duration: 2000})
    this.onCancel();
  }

  private onError(){
    this.snackBar.open('Error saving equipment', 'X', { duration: 2000})
  }
}
