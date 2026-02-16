import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProductAnalysisComponent } from './components/product-analysis/product-analysis.component';
import { ProductComparisonComponent } from './components/product-comparison/product-comparison.component';
import { PdfSummarizerComponent } from './components/pdf-summarizer/pdf-summarizer.component';
import { HealthInsightsComponent } from './components/health-insights/health-insights.component';
import { ChatbotComponent } from './components/chatbot/chatbot.component';
import { FooterComponent } from './components/footer/footer.component';
import { FormatMarkdownPipe } from './pipes/format-markdown.pipe';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    DashboardComponent,
    ProductAnalysisComponent,
    ProductComparisonComponent,
    PdfSummarizerComponent,
    HealthInsightsComponent,
    ChatbotComponent,
    FooterComponent,
    FormatMarkdownPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
