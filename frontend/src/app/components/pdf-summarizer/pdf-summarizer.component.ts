import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { DocumentSummaryResponse } from '../../models/models';

@Component({
  selector: 'app-pdf-summarizer',
  templateUrl: './pdf-summarizer.component.html',
  styleUrls: ['./pdf-summarizer.component.css']
})
export class PdfSummarizerComponent {

  selectedFile: File | null = null;
  fileName = '';
  summaryType = 'detailed';
  summaryResult: DocumentSummaryResponse | null = null;
  loading = false;
  error = '';
  dragOver = false;

  constructor(private apiService: ApiService) {}

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    this.setFile(file);
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.dragOver = true;
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    this.dragOver = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.dragOver = false;
    if (event.dataTransfer?.files.length) {
      this.setFile(event.dataTransfer.files[0]);
    }
  }

  private setFile(file: File): void {
    if (file && file.type === 'application/pdf') {
      this.selectedFile = file;
      this.fileName = file.name;
      this.error = '';
    } else {
      this.error = 'Please select a valid PDF file.';
      this.selectedFile = null;
      this.fileName = '';
    }
  }

  summarize(): void {
    if (!this.selectedFile) {
      this.error = 'Please select a PDF file first.';
      return;
    }

    this.loading = true;
    this.error = '';
    this.summaryResult = null;

    const observable = this.summaryType === 'brief'
      ? this.apiService.summarizeDocumentBrief(this.selectedFile)
      : this.apiService.summarizeDocument(this.selectedFile);

    observable.subscribe({
      next: (result) => {
        this.summaryResult = result;
        this.loading = false;
      },
      error: (err) => {
        this.error = err.error?.message || 'Failed to summarize document. Please try again.';
        this.loading = false;
      }
    });
  }

  clearFile(): void {
    this.selectedFile = null;
    this.fileName = '';
    this.summaryResult = null;
    this.error = '';
  }
}
