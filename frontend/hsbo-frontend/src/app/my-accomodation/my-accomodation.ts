import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule, DatePipe } from '@angular/common'; // Import CommonModule and DatePipe
import { FormsModule } from '@angular/forms'; // Import FormsModule if needed for two-way binding, though not directly used here
// Define interfaces for the data structure
interface HousingUnit {
  id: string;
  name: string;
  description: string;
  category: string;
  city: string;
  zipCode: string;
  address: string;
  freeSpaces: number;
}

interface Accommodation {
  id: string;
  studentId: string;
  startDate: string; // ISO 8601 string
  expiryDate: string | null; // ISO 8601 string or null
  housingUnit: HousingUnit;
}

@Component({
  selector: 'app-my-accomodation',
  standalone: false,
  templateUrl: './my-accomodation.html',
  styleUrl: './my-accomodation.css'
})
export class MyAccomodation {
accommodation: Accommodation | null = null;
isLoading: boolean = true;
errorMessage: string | null = null;
isDeleting: boolean = false;

constructor(private http: HttpClient) {}

ngOnInit(): void {
  this.fetchAccommodation();
}

fetchAccommodation(): void {
  this.isLoading = true;
  this.errorMessage = null;
  const apiUrl = 'http://localhost:8085/api/x-road/housingUnit/getMyBooking';

  this.http.get<Accommodation>(apiUrl).subscribe({
    next: (data) => {
      this.accommodation = data;
      this.isLoading = false;
    },
    error: (error) => {
      console.error('Error fetching accommodation:', error);
      this.errorMessage = 'Failed to load accommodation data. Please try again later.';
      this.isLoading = false; 
    }
  });
}

 deleteBooking(): void {
  if (!confirm('Sind Sie sicher, dass Sie diese Buchung löschen möchten? Diese Aktion kann nicht rückgängig gemacht werden.')) {
    return;
  }

  this.isDeleting = true;
  this.errorMessage = null;
  const url = 'http://localhost:8085/api/x-road/housingUnit/';

  this.http.delete(url, { responseType: 'text' }).subscribe({
    next: (response) => {
      this.accommodation = null;
    },
    error: (err) => {
      console.error('Error deleting booking:', err);
      this.errorMessage = 'Failed to delete the booking. Please try again.';
      this.isDeleting = false; 
    },
    complete: () => {
      this.isDeleting = false; 
    }
  });
}
}
