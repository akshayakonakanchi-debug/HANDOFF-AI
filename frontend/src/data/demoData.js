export const DEMO_PROJECT = {
  name: 'College Cultural Fest 2026',
  description: 'Annual college cultural festival event planning and execution',
  teamSize: 4,
  evidenceText: `
PROJECT: College Cultural Fest 2026

TEAM:
- Event Lead
- Sponsorship Lead
- Technical Lead
- Volunteer Lead

EVIDENCE:

COMPLETED:
- Venue booking confirmed
- Food vendor selected and contracted
- Event date finalized (March 15, 2026)
- Initial theme approved

IN PROGRESS:
- Stage setup and equipment arrangement
- Sound system and technical infrastructure
- Marketing campaign launch
- Sponsor communication

NOT STARTED:
- Volunteer allocation and training
- Final event checklist preparation
- VIP guest confirmations
- Day-of logistics planning

BLOCKED:
- Sponsor payment confirmation (awaiting bank transfer)

DEPENDENCIES:
Venue Booking → Venue Access → Stage Setup → Event Day
Sponsor Confirmation → Final Budget → Event Execution
Volunteer Allocation → Event Day Logistics

RISKS:
- Sponsor payment not yet confirmed (HIGH RISK)
- Volunteer allocation behind schedule (MEDIUM RISK)
- Stage setup requires venue access first (MEDIUM RISK)
- Sound system equipment delivery uncertain (LOW RISK)

NEXT STEPS:
1. Confirm sponsor payment status with finance team
2. Receive venue access confirmation
3. Begin stage setup and technical installation
4. Complete volunteer recruitment and training
5. Finalize event checklist and logistics
6. Confirm all VIP guests

MEETING NOTES (Latest):
"Venue has been confirmed and setup access will be available 2 days before event.
Stage team is ready to begin setup after venue access is received.
Sponsor has not yet confirmed payment - this is blocking final budget approval.
Volunteer assignments are still pending leader assignment.
Technical team is ready to begin infrastructure setup."

SCHEDULE:
- Stage setup: 2 days before event
- Volunteer allocation: within 1 week
- Event date: March 15, 2026 (3 days away)
- Final checklist: 1 day before event
  `
};

export const createDemoProject = async (api) => {
  try {
    const createdProject = await api.createProject(DEMO_PROJECT);
    return createdProject;
  } catch (error) {
    console.error('Error creating demo project:', error);
    return null;
  }
};
