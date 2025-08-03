import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

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
  private readonly API_BASE_URL = 'http://localhost:8085/api/x-road';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchLanguageCourses();
  }

  /**
   * Fetches the list of language courses from the backend API.
   */
  fetchLanguageCourses(): void {
    this.http.get<LanguageCourse[]>(`${this.API_BASE_URL}/languageCourses`)
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

  /**
   * Sends a request to book a specific language course.
   * @param courseId The ID of the course to book.
   */
  bookCourse(courseId: string): void {
    // Assuming the booking API expects a POST request to /languageCourse/{id}
    // and the body might be empty or contain minimal data if needed by the backend.
    // For this example, we'll send an empty object.
    this.http.post(`${this.API_BASE_URL}/languageCourse/${courseId}`, {})
      .subscribe({
        next: (response) => {
          console.log('Course booked successfully:', response);
          this.bookingMessage = `Successfully booked course with ID: ${courseId}!`;
          // Optionally, refresh the list to reflect updated available slots
          this.fetchLanguageCourses();
        },
        error: (error) => {
          console.error('Error booking course:', error);
          this.bookingMessage = `Failed to book course with ID: ${courseId}. ${error.error?.message || 'Please try again.'}`;
        }
      });
  }
}
