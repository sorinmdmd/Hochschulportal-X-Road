import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Accomodation } from './accomodation';

describe('Accomodation', () => {
  let component: Accomodation;
  let fixture: ComponentFixture<Accomodation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Accomodation]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Accomodation);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
