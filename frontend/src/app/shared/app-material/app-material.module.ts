import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule, MatOption } from '@angular/material/core';
import { MatSelect } from '@angular/material/select';
import { MatGridList } from '@angular/material/grid-list';
import { MatStepperModule } from '@angular/material/stepper';
import { MatDatepickerModule } from '@angular/material/datepicker';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatCardModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatFormField,
    MatLabel,
    MatInputModule,
    ReactiveFormsModule,
    MatOption,
    MatSelect,
    MatGridList,
    MatStepperModule,
    MatDatepickerModule,
    MatNativeDateModule 
  ],
  exports: [
    MatToolbarModule,
    MatCardModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatFormField,
    MatLabel,
    MatInputModule,
    ReactiveFormsModule,
    MatOption,
    MatSelect,
    MatGridList,
    MatStepperModule,
    MatDatepickerModule,
    MatNativeDateModule
  ]
})
export class AppMaterialModule { }