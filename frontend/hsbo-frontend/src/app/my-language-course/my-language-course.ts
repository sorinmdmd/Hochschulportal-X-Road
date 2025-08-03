import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface LanguageCourse {
  id?: string;
  language: string;
  duration: string;
  start: string; // ISO string
  end: string;
  availableSlots: number;
  group: string;
  location: string;
  tutorName: string;
}

interface LanguageCourseBooking {
  id?: string;
  studentId?: string;
  course?: LanguageCourse;
}

@Component({
  selector: 'app-my-language-course',
  standalone: false,
  templateUrl: './my-language-course.html',
  styleUrl: './my-language-course.css'
})
export class MyLanguageCourse implements OnInit {
  bookings: LanguageCourseBooking[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<LanguageCourseBooking[]>('http://localhost:8085/api/x-road/myLanguageCourses')
      .subscribe(data => {
        this.bookings = data;
      });
  }
}
