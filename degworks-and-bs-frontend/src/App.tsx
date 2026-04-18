import { useState } from "react";
import { StudentHeader } from "@/components/degree-works/student-header";
import { GPACard } from "@/components/degree-works/gpa-card";
import { CreditsProgress } from "@/components/degree-works/credits-progress";
import { RequirementsList } from "@/components/degree-works/requirements-list";
import { BrightspaceCourses } from "@/components/degree-works/brightspace-courses";
import { ProfessorRatings } from "@/components/degree-works/professor-ratings";
import { SemesterPlanner } from "@/components/degree-works/semester-planner";
import { Navigation } from "@/components/degree-works/navigation";

// Enhanced sample data
const studentData = {
  name: "Alexandra Chen",
  email: "achen@university.edu",
  studentId: "2021045892",
  major: "Computer Science, B.S.",
  minor: "Mathematics",
  college: "College of Engineering",
  expectedGraduation: "May 2025",
  advisor: "Dr. Sarah Mitchell",
  standing: "Dean's List" as const,
  enrollmentStatus: "Full-time" as const,
};

const creditCategories = [
  { name: "Core Requirements", completed: 30, required: 36, inProgress: 6, color: "#550C18" },
  { name: "Major Courses", completed: 42, required: 54, inProgress: 9, color: "#443730" },
  { name: "General Education", completed: 24, required: 24, inProgress: 0, color: "#786452" },
  { name: "Electives", completed: 9, required: 12, inProgress: 3, color: "#A5907E" },
];

const requirements = [
  {
    id: "1",
    name: "Core Computer Science",
    status: "completed" as const,
    courses: [
      { code: "CS 101", name: "Introduction to Programming", credits: 3, grade: "A", semester: "Fall 2021" },
      { code: "CS 201", name: "Data Structures", credits: 3, grade: "A-", semester: "Spring 2022" },
      { code: "CS 202", name: "Algorithms", credits: 3, grade: "B+", semester: "Fall 2022" },
    ],
  },
  {
    id: "2",
    name: "Advanced Theory",
    status: "in-progress" as const,
    courses: [
      { code: "CS 340", name: "Operating Systems", credits: 3, semester: "Current" },
      { code: "CS 350", name: "Database Systems", credits: 3, semester: "Current" },
    ],
    notes: "Complete both courses with a grade of C or better",
  },
  {
    id: "3",
    name: "Capstone Project",
    status: "not-started" as const,
    courses: [
      { code: "CS 490", name: "Senior Capstone", credits: 6 },
    ],
    notes: "Prerequisites: 90+ credits and department approval",
  },
  {
    id: "4",
    name: "Mathematics Core",
    status: "at-risk" as const,
    courses: [
      { code: "MATH 301", name: "Linear Algebra", credits: 3, grade: "C-", semester: "Fall 2023" },
      { code: "MATH 302", name: "Discrete Mathematics", credits: 3, semester: "Spring 2024" },
    ],
    notes: "MATH 301 grade below requirement - retake recommended",
  },
  {
    id: "5",
    name: "Technical Electives",
    status: "in-progress" as const,
    courses: [
      { code: "CS 410", name: "Machine Learning", credits: 3 },
      { code: "CS 420", name: "Computer Networks", credits: 3 },
      { code: "CS 430", name: "Software Engineering", credits: 3 },
    ],
    notes: "Choose 3 courses from approved list (9 credits required)",
  },
];

const brightspaceCourses = [
  {
    id: "1",
    code: "CS 340",
    name: "Operating Systems",
    professor: "Dr. James Wilson",
    professorId: "1",
    currentGrade: "91.2%",
    letterGrade: "A-",
    nextClass: "Mon 10:00 AM",
    location: "ENG 201",
    unreadAnnouncements: 2,
    progress: 65,
    assignments: [
      { name: "Process Scheduling Lab", dueDate: "Mar 15", status: "pending" as const, weight: 15 },
      { name: "Memory Management Essay", dueDate: "Mar 20", status: "pending" as const, weight: 10 },
    ],
  },
  {
    id: "2",
    code: "CS 350",
    name: "Database Systems",
    professor: "Dr. Maria Garcia",
    professorId: "2",
    currentGrade: "87.5%",
    letterGrade: "B+",
    nextClass: "Tue 2:00 PM",
    location: "CS 105",
    unreadAnnouncements: 0,
    progress: 70,
    assignments: [
      { name: "SQL Query Project", dueDate: "Mar 12", status: "graded" as const, grade: "95%" },
      { name: "Normalization Quiz", dueDate: "Mar 18", status: "pending" as const },
    ],
  },
  {
    id: "3",
    code: "MATH 302",
    name: "Discrete Mathematics",
    professor: "Dr. Robert Lee",
    professorId: "3",
    currentGrade: "94.0%",
    letterGrade: "A",
    nextClass: "Wed 11:00 AM",
    location: "MATH 302",
    unreadAnnouncements: 1,
    progress: 60,
    assignments: [
      { name: "Graph Theory HW", dueDate: "Mar 14", status: "graded" as const, grade: "95%" },
    ],
  },
  {
    id: "4",
    code: "PHYS 201",
    name: "Physics for Engineers",
    professor: "Dr. Emily Brown",
    professorId: "4",
    currentGrade: "82.3%",
    letterGrade: "B",
    nextClass: "Thu 9:00 AM",
    location: "PHYS 110",
    unreadAnnouncements: 0,
    progress: 55,
    assignments: [
      { name: "Lab Report #5", dueDate: "Mar 16", status: "pending" as const },
    ],
  },
];

const professors = [
  {
    id: "1",
    name: "Dr. James Wilson",
    department: "Computer Science",
    overallRating: 4.2,
    wouldTakeAgain: 85,
    difficultyLevel: 3.5,
    totalRatings: 127,
    topTags: ["Clear lectures", "Helpful", "Tough grader", "Accessible", "Lots of homework"],
    courses: ["CS 340", "CS 240", "CS 140"],
    recentReviews: [
      {
        id: "r1",
        course: "CS 340",
        rating: 4,
        difficulty: 4,
        comment: "Great professor who really knows the material. Exams are tough but fair. Office hours are super helpful.",
        date: "Feb 2024",
        helpful: 12,
        tags: ["Tough exams", "Great lectures"],
        grade: "A-",
      },
      {
        id: "r2",
        course: "CS 240",
        rating: 5,
        difficulty: 3,
        comment: "One of the best CS professors. Makes complex topics easy to understand.",
        date: "Dec 2023",
        helpful: 24,
        tags: ["Amazing lectures", "Caring"],
        grade: "A",
      },
    ],
  },
  {
    id: "2",
    name: "Dr. Maria Garcia",
    department: "Computer Science",
    overallRating: 4.7,
    wouldTakeAgain: 92,
    difficultyLevel: 3.0,
    totalRatings: 89,
    topTags: ["Amazing lectures", "Inspirational", "Caring", "Extra credit", "Clear grading"],
    courses: ["CS 350", "CS 250"],
    recentReviews: [
      {
        id: "r3",
        course: "CS 350",
        rating: 5,
        difficulty: 3,
        comment: "Absolutely fantastic! Her passion for databases is contagious. Very approachable and wants students to succeed.",
        date: "Jan 2024",
        helpful: 18,
        tags: ["Inspirational", "Clear grading"],
        grade: "A",
      },
    ],
  },
  {
    id: "3",
    name: "Dr. Robert Lee",
    department: "Mathematics",
    overallRating: 3.8,
    wouldTakeAgain: 68,
    difficultyLevel: 4.2,
    totalRatings: 156,
    topTags: ["Lots of homework", "Tough grader", "Skip textbook", "Lecture heavy", "Fair exams"],
    courses: ["MATH 302", "MATH 301", "MATH 201"],
    recentReviews: [
      {
        id: "r4",
        course: "MATH 302",
        rating: 4,
        difficulty: 4,
        comment: "Challenging but rewarding. You will learn a lot if you put in the effort. Weekly problem sets are time-consuming.",
        date: "Feb 2024",
        helpful: 8,
        tags: ["Heavy workload", "Fair exams"],
        grade: "B+",
      },
    ],
  },
  {
    id: "4",
    name: "Dr. Emily Brown",
    department: "Physics",
    overallRating: 4.0,
    wouldTakeAgain: 75,
    difficultyLevel: 3.8,
    totalRatings: 203,
    topTags: ["Engaging", "Lab focused", "Helpful TAs", "Curved grading"],
    courses: ["PHYS 201", "PHYS 101"],
    recentReviews: [
      {
        id: "r5",
        course: "PHYS 201",
        rating: 4,
        difficulty: 4,
        comment: "Labs are intense but you learn a lot. The curve at the end helps significantly.",
        date: "Jan 2024",
        helpful: 15,
        tags: ["Lab focused", "Curved"],
        grade: "B",
      },
    ],
  },
];

const plannedSemesters = [
  {
    id: "0",
    name: "Spring 2024",
    year: 2024,
    season: "Spring" as const,
    totalCredits: 15,
    isCurrent: true,
    courses: [
      { code: "CS 340", name: "Operating Systems", credits: 3, status: "confirmed" as const },
      { code: "CS 350", name: "Database Systems", credits: 3, status: "confirmed" as const },
      { code: "MATH 302", name: "Discrete Mathematics", credits: 3, status: "confirmed" as const },
      { code: "PHYS 201", name: "Physics for Engineers", credits: 3, status: "confirmed" as const },
      { code: "ENGL 101", name: "Composition I", credits: 3, status: "confirmed" as const },
    ],
  },
  {
    id: "1",
    name: "Fall 2024",
    year: 2024,
    season: "Fall" as const,
    totalCredits: 15,
    courses: [
      { code: "CS 410", name: "Machine Learning", credits: 3, status: "confirmed" as const },
      { code: "CS 420", name: "Computer Networks", credits: 3, status: "confirmed" as const },
      { code: "CS 430", name: "Software Engineering", credits: 3, status: "tentative" as const },
      { code: "MATH 350", name: "Statistics", credits: 3, status: "waitlist" as const },
      { code: "ENGL 200", name: "Technical Writing", credits: 3, status: "confirmed" as const },
    ],
  },
  {
    id: "2",
    name: "Spring 2025",
    year: 2025,
    season: "Spring" as const,
    totalCredits: 12,
    courses: [
      { code: "CS 490", name: "Senior Capstone", credits: 6, status: "tentative" as const },
      { code: "CS 450", name: "Cybersecurity", credits: 3, status: "tentative" as const },
      { code: "CS 460", name: "Cloud Computing", credits: 3, status: "tentative" as const },
    ],
  },
];

function App() {
  const [activeTab, setActiveTab] = useState("overview");
  const [highlightedProfessor, setHighlightedProfessor] = useState<string | undefined>();

  const handleProfessorClick = (professorId: string) => {
    setHighlightedProfessor(professorId);
    setActiveTab("professors");
  };

  return (
    <div className="min-h-screen bg-background">
      <StudentHeader student={studentData} />
      <Navigation activeTab={activeTab} onTabChange={setActiveTab} />

      <main className="max-w-7xl mx-auto px-4 py-4">
        {activeTab === "overview" && (
          <div className="space-y-4">
            {/* Top Row: GPA + Credits */}
            <div className="grid gap-4 lg:grid-cols-3">
              <GPACard
                cumulativeGPA={3.42}
                majorGPA={3.58}
                semesterGPA={3.65}
                trend="up"
                targetGPA={3.5}
                creditsAttempted={105}
                qualityPoints={359.1}
              />
              <div className="lg:col-span-2">
                <CreditsProgress
                  totalCredits={105}
                  requiredCredits={126}
                  inProgressCredits={18}
                  categories={creditCategories}
                />
              </div>
            </div>

            {/* Middle Row: Current Courses */}
            <BrightspaceCourses 
              courses={brightspaceCourses} 
              onProfessorClick={handleProfessorClick}
            />

            {/* Bottom Row: Requirements + Top Professors */}
            <div className="grid gap-4 lg:grid-cols-2">
              <RequirementsList requirements={requirements} compact />
              <ProfessorRatings professors={professors.slice(0, 2)} compact />
            </div>
          </div>
        )}

        {activeTab === "courses" && (
          <div className="space-y-4">
            <BrightspaceCourses 
              courses={brightspaceCourses}
              onProfessorClick={handleProfessorClick}
            />
            <RequirementsList requirements={requirements} />
          </div>
        )}

        {activeTab === "professors" && (
          <ProfessorRatings 
            professors={professors} 
            highlightedProfessorId={highlightedProfessor}
          />
        )}

        {activeTab === "planner" && (
          <div className="space-y-4">
            <SemesterPlanner semesters={plannedSemesters} />
            <div className="grid gap-4 lg:grid-cols-2">
              <CreditsProgress
                totalCredits={105}
                requiredCredits={126}
                inProgressCredits={18}
                categories={creditCategories}
              />
              <RequirementsList requirements={requirements.filter(r => r.status !== "completed")} compact />
            </div>
          </div>
        )}
      </main>

      <footer className="bg-card border-t border-border mt-8">
        <div className="max-w-7xl mx-auto px-4 py-4 flex items-center justify-between text-[10px] text-muted-foreground">
          <p>DegreeWorks Academic Tracking System v2.1</p>
          <div className="flex items-center gap-4">
            <span>Last synced: Today, 10:32 AM</span>
            <span>Data from Brightspace LMS & RateMyProfessor</span>
          </div>
        </div>
      </footer>
    </div>
  );
}

export default App;
