import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalAssignmentDepartmentUserComponent } from './modal-assignment-department-user.component';

describe('ModalAssignmentDepartmentUserComponent', () => {
  let component: ModalAssignmentDepartmentUserComponent;
  let fixture: ComponentFixture<ModalAssignmentDepartmentUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalAssignmentDepartmentUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalAssignmentDepartmentUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
