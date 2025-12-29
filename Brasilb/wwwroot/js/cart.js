(function(){
    // Cart page JS extracted from Views/Cart/Index.cshtml
    document.addEventListener('DOMContentLoaded', function() {
        // Initialisation des tooltips Bootstrap
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });

        // Gestion de la quantité
        document.querySelectorAll('.quantity-btn').forEach(button => {
            button.addEventListener('click', function() {
                const input = this.closest('.input-group').querySelector('input');
                let value = parseInt(input.value) || 0;

                if (this.classList.contains('decrement')) {
                    if (value > 1) {
                        input.value = value - 1;
                    }
                } else {
                    input.value = value + 1;
                }

                // Mettre à jour le formulaire
                input.dispatchEvent(new Event('change'));
            });
        });

        // Confirmation avant suppression
        document.querySelectorAll('.remove-item').forEach(button => {
            button.addEventListener('click', function(e) {
                if (!confirm('Êtes-vous sûr de vouloir supprimer cet article ?')) {
                    e.preventDefault();
                }
            });
        });

        // Gestion de l'affichage de la sélection de zone
        const deliveryOption = document.getElementById('deliveryOption');
        const pickupOption = document.getElementById('pickupOption');
        const dineInOption = document.getElementById('dineInOption');
        const zoneSelection = document.getElementById('zoneSelection');
        const zoneSelect = document.getElementById('zoneId');
        const deliveryFeeDisplay = document.getElementById('deliveryFeeDisplay');
        const checkoutButton = document.getElementById('checkoutButton');
        const zoneError = document.getElementById('zoneError');

        function setTypeCommandeValue(val) {
            // Les radio buttons gèrent déjà leurs valeurs, pas besoin de forcer
        }

        // Mettre à jour l'affichage en fonction de l'option sélectionnée
        function updateDeliveryOption() {
            if (deliveryOption && deliveryOption.checked) {
                zoneSelection.style.display = 'block';
                if (zoneSelect) zoneSelect.setAttribute('required', 'required');
                updateCheckoutButton();
            } else {
                zoneSelection.style.display = 'none';
                if (zoneSelect) zoneSelect.removeAttribute('required');
                if (checkoutButton) checkoutButton.disabled = false;
                if (zoneError) zoneError.style.display = 'none';
                const feeContainer = document.getElementById('deliveryFeeContainer');
                if (feeContainer) feeContainer.style.display = 'none';
                updateTotal(0);
            }
        }

        // Mettre à jour le bouton de paiement et les frais de livraison
        function updateCheckoutButton() {
            if (deliveryOption && deliveryOption.checked) {
                const selectedZone = zoneSelect ? zoneSelect.value : null;
                if (!selectedZone) {
                    if (checkoutButton) checkoutButton.disabled = true;
                    if (zoneError) zoneError.style.display = 'block';
                    const feeContainer = document.getElementById('deliveryFeeContainer');
                    if (feeContainer) feeContainer.style.display = 'none';
                } else {
                    if (checkoutButton) checkoutButton.disabled = false;
                    if (zoneError) zoneError.style.display = 'none';

                    // Mettre à jour l'affichage des frais de livraison
                    const selectedOption = zoneSelect.options[zoneSelect.selectedIndex];
                    const fee = parseFloat(selectedOption.getAttribute('data-fee')) || 0;
                    if (deliveryFeeDisplay) deliveryFeeDisplay.textContent = fee.toFixed(0) + ' FCFA';

                    // Afficher les frais de livraison et mettre à jour le total
                    const feeContainer = document.getElementById('deliveryFeeContainer');
                    if (feeContainer) feeContainer.style.display = 'flex';
                    updateTotal(fee);
                }
            } else {
                if (checkoutButton) checkoutButton.disabled = false;
                const feeContainer = document.getElementById('deliveryFeeContainer');
                if (feeContainer) feeContainer.style.display = 'none';
                updateTotal(0);
            }
        }

        // Mettre à jour le total avec les frais de livraison
        function updateTotal(deliveryFee) {
            const subtotal = window.cartSubtotal || 0;
            const total = subtotal + deliveryFee;
            const el = document.getElementById('grandTotal');
            if (el) el.textContent = total.toFixed(0);
        }

        // Événements
        if (deliveryOption) deliveryOption.addEventListener('change', updateDeliveryOption);
        if (pickupOption) pickupOption.addEventListener('change', updateDeliveryOption);
        if (dineInOption) dineInOption.addEventListener('change', updateDeliveryOption);
        if (zoneSelect) zoneSelect.addEventListener('change', updateCheckoutButton);

        // Initialisation
        updateDeliveryOption();

        // Gestion de la soumission du formulaire
        const form = document.getElementById('checkoutForm');
        if (form) {
            form.addEventListener('submit', function(e) {
                // Réinitialiser l'état d'erreur
                if (zoneError) zoneError.style.display = 'none';

                // Validation pour la livraison seulement
                if (deliveryOption && deliveryOption.checked) {
                    if (!zoneSelect || !zoneSelect.value) {
                        e.preventDefault();
                        if (zoneError) zoneError.style.display = 'block';
                        if (zoneSelect) zoneSelect.focus();
                        return false;
                    }
                }

                // Désactiver le bouton pour éviter les doubles soumissions
                if (checkoutButton) {
                    checkoutButton.disabled = true;
                    checkoutButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Traitement...';
                }

                return true;
            });
        }
    });
})();
