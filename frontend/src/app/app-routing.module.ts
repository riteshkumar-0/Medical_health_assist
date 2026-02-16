import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProductAnalysisComponent } from './components/product-analysis/product-analysis.component';
import { ProductComparisonComponent } from './components/product-comparison/product-comparison.component';
import { PdfSummarizerComponent } from './components/pdf-summarizer/pdf-summarizer.component';
import { HealthInsightsComponent } from './components/health-insights/health-insights.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'analysis', component: ProductAnalysisComponent },
  { path: 'comparison', component: ProductComparisonComponent },
  { path: 'pdf-summarizer', component: PdfSummarizerComponent },
  { path: 'health-insights', component: HealthInsightsComponent },
  { path: '**', redirectTo: '/dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
