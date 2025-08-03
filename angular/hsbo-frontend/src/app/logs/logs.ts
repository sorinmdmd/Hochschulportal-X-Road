import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


interface LogEntry {
  log_time: string;
  queryid: string;
  http_method: string;
  url_path: string;
  message_flattened: string;
}

@Component({
  selector: 'app-logs',
  standalone: false,
  templateUrl: './logs.html',
  styleUrl: './logs.css'
})
export class Logs implements OnInit{
// Array to hold the fetched log entries
logs: LogEntry[] = [];
// Property to store any error messages
errorMessage: string = '';
// The API endpoint URL
private apiUrl = 'http://localhost:8085/api/x-road/housingUnits/getLogs';

constructor(private http: HttpClient) { }

/**
 * Lifecycle hook that is called after Angular has initialized all data-bound properties of a directive.
 * Fetch logs when the component initializes.
 */
ngOnInit(): void {
  this.fetchLogs();
}

/**
 * Fetches log data from the API endpoint.
 */
fetchLogs(): void {
  this.http.get<LogEntry[]>(this.apiUrl)
    .pipe(
      // Catch any errors that occur during the HTTP request
      catchError(error => {
        console.error('Error fetching logs:', error);
        this.errorMessage = 'Failed to load logs. Please ensure the backend server is running and accessible at ' + this.apiUrl + '.';
        // Return an observable with a user-facing error message
        return throwError(() => new Error(this.errorMessage));
      })
    )
    .subscribe(data => {
      // Assign the fetched data to the logs array
      this.logs = data;
      this.errorMessage = ''; // Clear any previous error messages on successful fetch
    });
}
}
