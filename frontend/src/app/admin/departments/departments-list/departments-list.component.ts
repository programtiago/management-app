import { Component, Input, OnInit } from '@angular/core';
import { Department } from '../../../model/department';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-departments-list',
  templateUrl: './departments-list.component.html',
  styleUrl: './departments-list.component.scss'
})
export class DepartmentsListComponent implements OnInit{

  @Input() departments: Department[] = []

  constructor(private router: Router, private route: ActivatedRoute, private dialog: MatDialog,
    private adminService: AdminService, private snackBar: MatSnackBar
  ){}

  ngOnInit(): void {
    this.adminService.listDepartments().subscribe((res) => {
      this.departments = res;
    })  
 
  }

  openMenuForDepartments(){
    
  }



}
