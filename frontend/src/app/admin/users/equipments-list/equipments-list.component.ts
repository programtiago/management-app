import { Component, Input, OnInit } from '@angular/core';
import { Equipment } from '../../../model/equiment';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-equipments-list',
  templateUrl: './equipments-list.component.html',
  styleUrl: './equipments-list.component.scss'
})
export class EquipmentsListComponent implements OnInit{
  
  @Input() equipments: Equipment[] = [];
  statusEquipment: string = ""

  displayedColumns: String[] = ['brand', 'model', 'description', 'serialNumber', 'registryDate', 'status', 'actions']

  constructor(private adminService: AdminService, private snackbar: MatSnackBar, private dialog: MatDialog){
     
  }

  ngOnInit(): void {
    
  }
}
