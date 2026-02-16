import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatMarkdown'
})
export class FormatMarkdownPipe implements PipeTransform {

  transform(value: string): string {
    if (!value) return '';

    let html = value;

    html = html.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');

    html = html.replace(/(?<!\*)\*(?!\*)(.*?)(?<!\*)\*(?!\*)/g, '<em>$1</em>');

    html = html.replace(/^### (.*$)/gm, '<h5 class="fw-bold mt-3 mb-2">$1</h5>');
    html = html.replace(/^## (.*$)/gm, '<h4 class="fw-bold mt-4 mb-2">$1</h4>');
    html = html.replace(/^# (.*$)/gm, '<h3 class="fw-bold mt-4 mb-3">$1</h3>');

    html = html.replace(/^\d+\.\s+(.*$)/gm, '<li>$1</li>');

    html = html.replace(/^[-•]\s+(.*$)/gm, '<li>$1</li>');

    html = html.replace(/\n/g, '<br>');

    return html;
  }
}
