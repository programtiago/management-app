import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalGenerateAssignmentTermComponent } from './modal-generate-assignment-term.component';

describe('ModalGenerateAssignmentTermComponent', () => {
  let component: ModalGenerateAssignmentTermComponent;
  let fixture: ComponentFixture<ModalGenerateAssignmentTermComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalGenerateAssignmentTermComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalGenerateAssignmentTermComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
