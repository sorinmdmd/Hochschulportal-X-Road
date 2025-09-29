import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { environment } from '../enviroments/enviroment'

interface LanguageCourse {
  id: string;
  language: string;
  duration: string;
  start: string;
  end: string;
  availableSlots: number;
  group: string;
  location: string;
  tutorName: string;
}

@Component({
  selector: 'app-language-courses',
  standalone: false,
  templateUrl: './language-courses.html',
  styleUrl: './language-courses.css'
})
export class LanguageCourses implements OnInit {
  languageCourses: LanguageCourse[] = [];
  bookingMessage: string = '';
  private baseUrl = environment.backendConfig.baseUrl;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchLanguageCourses();
  }

  fetchLanguageCourses(): void {
    this.http.get<LanguageCourse[]>(`${this.baseUrl}/x-road/languageCourses`)
      .subscribe({
        next: (data) => {
          this.languageCourses = data;
        },
        error: (error) => {
          console.error('Error fetching language courses:', error);
          this.bookingMessage = 'Failed to load language courses. Please try again later.';
        }
      });
  }

  bookCourse(courseId: string): void {
   
    this.http.post(`${this.baseUrl}/x-road/languageCourse/${courseId}`, {})
      .subscribe({
        next: (response) => {
          this.bookingMessage = `Successfully booked course with ID: ${courseId}!`;
          this.fetchLanguageCourses();
        },
        error: (error) => {
          console.error('Error booking course:', error);
          this.bookingMessage = `Failed to book course with ID: ${courseId}. ${error.error?.message || 'Please try again.'}`;
        }
      });
  }
}
