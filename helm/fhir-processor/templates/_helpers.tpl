{{- define "fhir-processor.name" -}}
{{ .Chart.Name }}
{{- end }}

{{- define "fhir-processor.labels" -}}
app.kubernetes.io/name: {{ include "fhir-processor.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
