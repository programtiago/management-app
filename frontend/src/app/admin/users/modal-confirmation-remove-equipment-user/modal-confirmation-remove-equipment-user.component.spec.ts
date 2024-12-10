import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalConfirmationRemoveEquipmentUserComponent } from './modal-confirmation-remove-equipment-user.component';

describe('ModalConfirmationRemoveEquipmentUserComponent', () => {
  let component: ModalConfirmationRemoveEquipmentUserComponent;
  let fixture: ComponentFixture<ModalConfirmationRemoveEquipmentUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalConfirmationRemoveEquipmentUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalConfirmationRemoveEquipmentUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
