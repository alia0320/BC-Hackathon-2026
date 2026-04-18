import { CalendarDays, Plus, GripVertical, Trash2, Check, AlertCircle, Clock } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";

interface PlannedCourse {
  code: string;
  name: string;
  credits: number;
  status: "confirmed" | "tentative" | "waitlist";
  prerequisites?: string[];
  timeConflict?: boolean;
}

interface Semester {
  id: string;
  name: string;
  year: number;
  season: "Fall" | "Spring" | "Summer";
  courses: PlannedCourse[];
  totalCredits: number;
  isCurrent?: boolean;
}

interface SemesterPlannerProps {
  semesters: Semester[];
}

const statusConfig = {
  confirmed: {
    icon: Check,
    color: "text-[#15803d]",
    bgColor: "bg-[#dcfce7]",
    borderColor: "border-[#15803d]/30",
    label: "Confirmed",
  },
  tentative: {
    icon: Clock,
    color: "text-[#ca8a04]",
    bgColor: "bg-[#fef9c3]",
    borderColor: "border-[#ca8a04]/30",
    label: "Tentative",
  },
  waitlist: {
    icon: AlertCircle,
    color: "text-[#ea580c]",
    bgColor: "bg-orange-50",
    borderColor: "border-[#ea580c]/30",
    label: "Waitlist",
  },
};

const seasonColors = {
  Fall: "bg-[#550C18]",
  Spring: "bg-[#15803d]",
  Summer: "bg-[#ca8a04]",
};

export function SemesterPlanner({ semesters }: SemesterPlannerProps) {
  const totalPlannedCredits = semesters.reduce((acc, s) => acc + s.totalCredits, 0);
  const confirmedCredits = semesters.reduce((acc, s) => 
    acc + s.courses.filter(c => c.status === "confirmed").reduce((sum, c) => sum + c.credits, 0), 0
  );

  return (
    <div className="bg-card border border-border rounded-lg overflow-hidden">
      {/* Header */}
      <div className="px-3 py-2 border-b border-border bg-muted/30 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <CalendarDays className="h-4 w-4 text-[#550C18]" />
          <h3 className="text-xs font-semibold text-foreground uppercase tracking-wide">Semester Planner</h3>
        </div>
        <div className="flex items-center gap-3">
          <span className="text-[10px] text-muted-foreground">
            {confirmedCredits}/{totalPlannedCredits} credits confirmed
          </span>
          <Button size="sm" variant="outline" className="h-6 px-2 text-[10px]">
            <Plus className="h-3 w-3 mr-1" />
            Add
          </Button>
        </div>
      </div>

      <div className="p-3">
        {/* Timeline View */}
        <div className="flex gap-3 overflow-x-auto pb-2">
          {semesters.map((semester, idx) => {
            const config = seasonColors[semester.season];
            const coursesByStatus = {
              confirmed: semester.courses.filter(c => c.status === "confirmed"),
              tentative: semester.courses.filter(c => c.status === "tentative"),
              waitlist: semester.courses.filter(c => c.status === "waitlist"),
            };

            return (
              <div 
                key={semester.id} 
                className={`flex-shrink-0 w-56 border rounded-lg overflow-hidden ${
                  semester.isCurrent ? "border-[#550C18] ring-1 ring-[#550C18]/20" : "border-border"
                }`}
              >
                {/* Semester Header */}
                <div className={`${config} px-3 py-2 flex items-center justify-between`}>
                  <div className="flex items-center gap-2">
                    <span className="text-white font-semibold text-sm">
                      {semester.season} {semester.year}
                    </span>
                    {semester.isCurrent && (
                      <Badge className="bg-white/20 text-white text-[9px] h-4 px-1">Current</Badge>
                    )}
                  </div>
                  <Badge variant="secondary" className="bg-white/20 text-white text-[9px] h-4 px-1.5">
                    {semester.totalCredits} cr
                  </Badge>
                </div>

                {/* Courses */}
                <div className="p-2 space-y-1 min-h-[120px] bg-card">
                  {semester.courses.length > 0 ? (
                    semester.courses.map((course, cidx) => {
                      const statusCfg = statusConfig[course.status];
                      const StatusIcon = statusCfg.icon;

                      return (
                        <div
                          key={cidx}
                          className={`flex items-center gap-2 px-2 py-1.5 rounded border ${statusCfg.borderColor} ${statusCfg.bgColor} group`}
                        >
                          <GripVertical className="h-3 w-3 text-muted-foreground opacity-0 group-hover:opacity-100 cursor-grab" />
                          <div className="flex-1 min-w-0">
                            <div className="flex items-center gap-1.5">
                              <span className="font-mono font-medium text-[11px] text-foreground">{course.code}</span>
                              <StatusIcon className={`h-3 w-3 ${statusCfg.color}`} />
                            </div>
                            <p className="text-[9px] text-muted-foreground truncate">{course.name}</p>
                          </div>
                          <span className="text-[10px] text-muted-foreground">{course.credits}cr</span>
                          <button className="opacity-0 group-hover:opacity-100 transition-opacity">
                            <Trash2 className="h-3 w-3 text-muted-foreground hover:text-[#b91c1c]" />
                          </button>
                        </div>
                      );
                    })
                  ) : (
                    <div className="flex flex-col items-center justify-center h-full py-6 text-center">
                      <CalendarDays className="h-6 w-6 text-muted-foreground/50 mb-1" />
                      <p className="text-[10px] text-muted-foreground">No courses planned</p>
                      <Button variant="ghost" size="sm" className="h-5 px-2 text-[10px] mt-1 text-[#550C18]">
                        <Plus className="h-3 w-3 mr-1" />
                        Add course
                      </Button>
                    </div>
                  )}
                </div>

                {/* Status Summary */}
                {semester.courses.length > 0 && (
                  <div className="px-2 py-1.5 border-t border-border bg-muted/20 flex items-center justify-center gap-3">
                    {Object.entries(coursesByStatus).map(([status, courses]) => {
                      if (courses.length === 0) return null;
                      const cfg = statusConfig[status as keyof typeof statusConfig];
                      return (
                        <div key={status} className="flex items-center gap-1">
                          <div className={`w-1.5 h-1.5 rounded-full ${cfg.bgColor.replace('bg-', 'bg-').replace('/50', '')}`} 
                               style={{ backgroundColor: status === 'confirmed' ? '#15803d' : status === 'tentative' ? '#ca8a04' : '#ea580c' }} />
                          <span className="text-[9px] text-muted-foreground">{courses.length}</span>
                        </div>
                      );
                    })}
                  </div>
                )}
              </div>
            );
          })}

          {/* Add Semester Button */}
          <button className="flex-shrink-0 w-40 min-h-[180px] border-2 border-dashed border-border rounded-lg flex flex-col items-center justify-center gap-2 hover:border-[#550C18]/50 hover:bg-muted/30 transition-colors">
            <Plus className="h-6 w-6 text-muted-foreground" />
            <span className="text-[11px] text-muted-foreground">Add Semester</span>
          </button>
        </div>

        {/* Legend */}
        <div className="flex items-center justify-center gap-4 mt-3 pt-2 border-t border-border">
          {Object.entries(statusConfig).map(([status, cfg]) => (
            <div key={status} className="flex items-center gap-1.5 text-[10px] text-muted-foreground">
              <cfg.icon className={`h-3 w-3 ${cfg.color}`} />
              <span>{cfg.label}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
