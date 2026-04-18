import { ExternalLink, BookOpen, FileText, Clock, Bell, CheckCircle2, AlertCircle } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";

interface Assignment {
  name: string;
  dueDate: string;
  status: "pending" | "submitted" | "graded" | "late";
  grade?: string;
  weight?: number;
}

interface Course {
  id: string;
  code: string;
  name: string;
  professor: string;
  professorId?: string;
  currentGrade: string;
  letterGrade: string;
  nextClass: string;
  location?: string;
  assignments: Assignment[];
  unreadAnnouncements: number;
  progress?: number;
}

interface BrightspaceCoursesProps {
  courses: Course[];
  onProfessorClick?: (professorId: string) => void;
}

export function BrightspaceCourses({ courses, onProfessorClick }: BrightspaceCoursesProps) {
  const getGradeColor = (grade: string) => {
    if (grade.startsWith("A")) return "text-[#15803d] bg-[#dcfce7]";
    if (grade.startsWith("B")) return "text-[#550C18] bg-[#F7DAD9]";
    if (grade.startsWith("C")) return "text-[#ca8a04] bg-[#fef9c3]";
    return "text-[#b91c1c] bg-red-50";
  };

  const getAssignmentStatusIcon = (status: Assignment["status"]) => {
    switch (status) {
      case "graded": return <CheckCircle2 className="h-3 w-3 text-[#15803d]" />;
      case "submitted": return <CheckCircle2 className="h-3 w-3 text-[#0369a1]" />;
      case "late": return <AlertCircle className="h-3 w-3 text-[#b91c1c]" />;
      default: return <Clock className="h-3 w-3 text-[#786452]" />;
    }
  };

  // Calculate upcoming assignments across all courses
  const upcomingAssignments = courses
    .flatMap(c => c.assignments.filter(a => a.status === "pending").map(a => ({ ...a, courseCode: c.code })))
    .slice(0, 3);

  return (
    <div className="bg-card border border-border rounded-lg overflow-hidden">
      {/* Header */}
      <div className="px-3 py-2 border-b border-border bg-muted/30 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <BookOpen className="h-4 w-4 text-[#550C18]" />
          <h3 className="text-xs font-semibold text-foreground uppercase tracking-wide">Current Courses</h3>
          <Badge variant="secondary" className="text-[10px] h-4 px-1.5 bg-[#F7DAD9] text-[#550C18]">
            Brightspace
          </Badge>
        </div>
        <Button variant="ghost" size="sm" className="text-[#550C18] h-6 px-2 text-[10px]">
          Open LMS <ExternalLink className="h-3 w-3 ml-1" />
        </Button>
      </div>

      <div className="p-3">
        {/* Upcoming Assignments Alert */}
        {upcomingAssignments.length > 0 && (
          <div className="mb-3 p-2 bg-[#fef9c3] rounded-lg border border-[#ca8a04]/20">
            <div className="flex items-center gap-1.5 mb-1.5">
              <AlertCircle className="h-3 w-3 text-[#ca8a04]" />
              <span className="text-[10px] font-semibold text-[#ca8a04] uppercase">Upcoming Due</span>
            </div>
            <div className="space-y-1">
              {upcomingAssignments.map((a, idx) => (
                <div key={idx} className="flex items-center justify-between text-[11px]">
                  <span className="text-foreground">
                    <span className="font-mono font-medium">{a.courseCode}</span>
                    <span className="text-muted-foreground"> - {a.name}</span>
                  </span>
                  <span className="text-[#ca8a04] font-medium">{a.dueDate}</span>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Course Grid */}
        <div className="grid gap-2 sm:grid-cols-2">
          {courses.map((course) => (
            <div
              key={course.id}
              className="border border-border rounded-lg p-2.5 hover:border-[#550C18]/30 transition-colors"
            >
              {/* Course Header */}
              <div className="flex items-start justify-between mb-2">
                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-1.5">
                    <span className="font-mono font-semibold text-sm text-foreground">{course.code}</span>
                    {course.unreadAnnouncements > 0 && (
                      <Badge className="bg-[#b91c1c] text-white text-[9px] h-3.5 px-1 rounded-full">
                        {course.unreadAnnouncements}
                      </Badge>
                    )}
                  </div>
                  <p className="text-[11px] text-muted-foreground truncate">{course.name}</p>
                </div>
                <div className={`${getGradeColor(course.letterGrade)} px-2 py-1 rounded text-center`}>
                  <p className="text-sm font-bold">{course.letterGrade}</p>
                  <p className="text-[9px] opacity-80">{course.currentGrade}</p>
                </div>
              </div>

              {/* Professor & Schedule */}
              <div className="flex items-center justify-between text-[10px] mb-2">
                <button 
                  onClick={() => course.professorId && onProfessorClick?.(course.professorId)}
                  className="text-muted-foreground hover:text-[#550C18] transition-colors"
                >
                  {course.professor}
                </button>
                <div className="flex items-center gap-1 text-muted-foreground">
                  <Clock className="h-3 w-3" />
                  <span>{course.nextClass}</span>
                </div>
              </div>

              {/* Progress Bar */}
              {course.progress !== undefined && (
                <div className="mb-2">
                  <div className="h-1 bg-muted rounded-full overflow-hidden">
                    <div 
                      className="h-full bg-[#550C18] transition-all" 
                      style={{ width: `${course.progress}%` }}
                    />
                  </div>
                </div>
              )}

              {/* Assignments */}
              {course.assignments.length > 0 && (
                <div className="pt-2 border-t border-border space-y-1">
                  {course.assignments.slice(0, 2).map((assignment, idx) => (
                    <div key={idx} className="flex items-center gap-1.5 text-[10px]">
                      {getAssignmentStatusIcon(assignment.status)}
                      <span className="flex-1 truncate text-foreground">{assignment.name}</span>
                      {assignment.grade ? (
                        <span className="font-medium text-[#15803d]">{assignment.grade}</span>
                      ) : (
                        <span className="text-muted-foreground">{assignment.dueDate}</span>
                      )}
                    </div>
                  ))}
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
