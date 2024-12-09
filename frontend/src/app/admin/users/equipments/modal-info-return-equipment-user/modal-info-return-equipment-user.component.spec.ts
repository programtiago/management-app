import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalInfoReturnEquipmentUserComponent } from './modal-info-return-equipment-user.component';

describe('ModalInfoReturnEquipmentUserComponent', () => {
  let component: ModalInfoReturnEquipmentUserComponent;
  let fixture: ComponentFixture<ModalInfoReturnEquipmentUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalInfoReturnEquipmentUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalInfoReturnEquipmentUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
