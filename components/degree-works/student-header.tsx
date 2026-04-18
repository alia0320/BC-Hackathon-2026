import { User, Mail, Calendar, GraduationCap, Building2, Bell, Settings, ChevronDown } from "lucide-react";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";

interface StudentInfo {
  name: string;
  email: string;
  studentId: string;
  major: string;
  minor?: string;
  college: string;
  expectedGraduation: string;
  advisor: string;
  avatar?: string;
  standing: "Good" | "Probation" | "Dean's List";
  enrollmentStatus: "Full-time" | "Part-time";
}

interface StudentHeaderProps {
  student: StudentInfo;
}

export function StudentHeader({ student }: StudentHeaderProps) {
  const standingColors = {
    "Good": "bg-[#dcfce7] text-[#15803d]",
    "Probation": "bg-[#fef9c3] text-[#ca8a04]",
    "Dean's List": "bg-[#F7DAD9] text-[#550C18]",
  };

  return (
    <header className="bg-[#550C18] text-white">
      <div className="max-w-7xl mx-auto px-4 py-3">
        <div className="flex items-center justify-between">
          {/* Left: Logo & Student Info */}
          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <GraduationCap className="h-7 w-7" />
              <span className="font-serif font-bold text-lg hidden sm:inline">DegreeWorks</span>
            </div>
            
            <div className="h-8 w-px bg-white/20 hidden md:block" />
            
            <div className="flex items-center gap-3">
              <Avatar className="h-9 w-9 border-2 border-white/30">
                <AvatarImage src={student.avatar} alt={student.name} />
                <AvatarFallback className="bg-white/20 text-white text-xs font-semibold">
                  {student.name.split(" ").map((n) => n[0]).join("")}
                </AvatarFallback>
              </Avatar>
              <div className="hidden sm:block">
                <div className="flex items-center gap-2">
                  <span className="font-semibold text-sm">{student.name}</span>
                  <Badge className={`${standingColors[student.standing]} text-[10px] px-1.5 py-0 h-4`}>
                    {student.standing}
                  </Badge>
                </div>
                <p className="text-[11px] text-white/70">{student.studentId} | {student.major}</p>
              </div>
            </div>
          </div>

          {/* Right: Quick Actions */}
          <div className="flex items-center gap-2">
            <Button variant="ghost" size="sm" className="text-white/80 hover:text-white hover:bg-white/10 h-8 px-2">
              <Bell className="h-4 w-4" />
              <span className="sr-only">Notifications</span>
            </Button>
            <Button variant="ghost" size="sm" className="text-white/80 hover:text-white hover:bg-white/10 h-8 px-2">
              <Settings className="h-4 w-4" />
              <span className="sr-only">Settings</span>
            </Button>
          </div>
        </div>
      </div>

      {/* Secondary Info Bar */}
      <div className="bg-[#443730] border-t border-white/10">
        <div className="max-w-7xl mx-auto px-4 py-2">
          <div className="flex flex-wrap items-center gap-x-6 gap-y-1 text-[11px]">
            <div className="flex items-center gap-1.5 text-white/80">
              <Mail className="h-3 w-3" />
              <span>{student.email}</span>
            </div>
            <div className="flex items-center gap-1.5 text-white/80">
              <Building2 className="h-3 w-3" />
              <span>{student.college}</span>
            </div>
            <div className="flex items-center gap-1.5 text-white/80">
              <User className="h-3 w-3" />
              <span>Advisor: {student.advisor}</span>
            </div>
            <div className="flex items-center gap-1.5 text-white/80">
              <Calendar className="h-3 w-3" />
              <span>Expected: {student.expectedGraduation}</span>
            </div>
            {student.minor && (
              <div className="flex items-center gap-1.5 text-white/80">
                <GraduationCap className="h-3 w-3" />
                <span>Minor: {student.minor}</span>
              </div>
            )}
            <Badge variant="outline" className="text-[10px] border-white/30 text-white/80 h-4 px-1.5">
              {student.enrollmentStatus}
            </Badge>
          </div>
        </div>
      </div>
    </header>
  );
}
