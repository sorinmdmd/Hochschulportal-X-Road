import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguageCourses } from './language-courses';

describe('LanguageCourses', () => {
  let component: LanguageCourses;
  let fixture: ComponentFixture<LanguageCourses>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LanguageCourses]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LanguageCourses);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
